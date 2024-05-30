import fs from 'fs'
import * as process from 'process'
import csv from 'csv-parser'
import mysql from 'mysql2'

const pool = mysql.createPool({
  host: 'localhost',
  user: 'test',
  password: 'test',
  database: 'growing',
  waitForConnections: true,
  connectionLimit: 10,
  maxIdle: 10, // max idle connections, the default value is the same as `connectionLimit`
  idleTimeout: 60000, // idle connections timeout, in milliseconds, the default value 60000
  queueLimit: 0,
  enableKeepAlive: true,
  keepAliveInitialDelay: 0,
})

const pwd = '$2a$10$Mla2UOk7vkm7nq7uq/aVYuFQVk8k6onmVjo/TG9N2rK0tB7eaueBy'

const run = async () => {
  pool.getConnection((err, conn) => {
    conn.query(`select id from users where name = ?`, ['이순종'], (err, result) => {
      // 1. 텀 등록
      conn.execute(`insert ignore into term (id, created_at, updated_at, name, start_date, end_date, is_active)
                          values (1, NOW(), NOW(), '2023-2학기', '2023-09-03', '2024-02-28', 1);`)

      // 2. 등불 모임 등록
      conn.execute(
        `insert ignore into team (id, created_at, created_by, updated_at, updated_by, name, type, leader_id, manager_id, term_id)
                          values (1, now(), null, now(), null, '등불 모임', 'LAMP', ?, ?, 1);`,
        // @ts-ignore
        [result[0].id, result[0].id],
      )

      conn.release()
    })
  })

  fs.createReadStream('src/scripts/growing/2_lamp.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.query(
          `select id from users where name = ? and phone_number = ?`,
          [row.name, row.phone],
          (err, result) => {
            if (row.duty === 'PASTOR' || row.duty === 'GANSA') {
              // 3.1. 교역자, 간사 수정
              conn.execute(`update users set role = ?, password = ? where id = ?`, [
                'ADMIN',
                pwd,
                // @ts-ignore
                result[0].id,
              ])
            } else if (row.duty === 'CODY') {
              // 3.2. 코디 수정
              conn.execute(`update users set role = ?, password = ? where id = ?`, [
                'MANAGER',
                pwd,
                // @ts-ignore
                result[0].id,
              ])
            }

            // 4. 등불 멤버 등록
            conn.execute(
              `insert ignore into team_member (created_at, created_by, updated_at, updated_by, team_id, member_id, duty)
                                values (now(), null, now(), null, 1, ?, ?)`,

              // @ts-ignore
              [result[0].id, row.duty],
              (err, results, fields) => {
                if (err) {
                  console.error('Execute error', err)
                } else {
                  console.log('Results: ', results)
                }

                conn.release()
              },
            )
          },
        )
      })
    })
    .on('end', () => {
      setTimeout(() => {
        process.exit(0)
      }, 60000)
    })
}

run()
