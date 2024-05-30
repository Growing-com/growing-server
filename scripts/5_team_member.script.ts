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
  fs.createReadStream('src/scripts/growing/5_team_member.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.query(
          // 1. 멤버 조회
          `select id, name from users where name = ? and grade = ? and sex = ?`,
          [row.member, row.grade, row.sex === '남' ? 'MALE' : 'FEMALE'],
          (err, result1) => {
            conn.query(
              // 2. 팀 조회
              `select id, leader_id from team where (type = 'PLANT' or type = 'NEW') and name like ?`,
              // @ts-ignore
              [`${row.leader}%`],
              // 순모임
              (err, result2) => {
                // @ts-ignore
                conn.execute(
                  // 3. 팀 멤버 등록
                  `insert ignore into team_member (created_at, created_by, updated_at, updated_by, team_id, member_id, duty)
                                  values (now(), null, now(), null, ?, ?, ?)`,

                  [
                    // @ts-ignore
                    result2[0].id,
                    // @ts-ignore
                    result1[0].id,
                    // @ts-ignore
                    result1[0].id === result2[0].leader_id ? 'LEADER' : 'MEMBER',
                  ],
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
