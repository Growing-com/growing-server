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

const run = async () => {
  fs.createReadStream('src/scripts/growing/3_plant.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.query(
          // 1. 코디 조회
          `select id from users where name = ? and role = 'MANAGER'`,
          [row.cody],
          (err, result1) => {
            conn.query(
              // 2. 리더 조회
              `select id from users where name = ? and grade = ?`,
              [row.leader, row.grade],
              (err, result2) => {
                conn.execute(
                  // 3. 순모임 등록
                  `insert ignore into team (created_at, created_by, updated_at, updated_by, name, type, leader_id, manager_id, term_id) 
                                  values (now(), null, now(), null, ?,'PLANT', ?, ?, 1)`,
                  // @ts-ignore
                  [`${row.leader} 순모임`, result2[0].id, result1[0].id],
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
