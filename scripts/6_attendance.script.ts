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
  fs.createReadStream('src/scripts/growing/6_attendance.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.query(
          `select tm.id
                    from team_member tm
                    inner join users u on u.id = tm.member_id 
                    where u.name = ? and u.sex = ? and u.grade = ?`,
          [row.name, row.sex === '남' ? 'MALE' : 'FEMALE', row.grade],
          (err, result) => {
            Object.keys(row)
              .filter(k => k.startsWith('2023'))
              .filter(k => row[k] === '현장' || row[k] === '결석' || row[k] === '온라인')
              .map(k => {
                if (row[k] === '현장') {
                  // @ts-ignore
                  return [result[0].id, k, 'ATTEND']
                } else if (row[k] === '온라인') {
                  // @ts-ignore
                  return [result[0].id, k, 'ONLINE']
                } else {
                  // @ts-ignore
                  return [result[0].id, k, 'ABSENT']
                }
              })
              .map(el => {
                return `insert ignore into attendance(created_at, created_by, updated_at, updated_by, team_member_id, week, status, etc) value (now(), null, now(), null, ${el[0]}, '${el[1]}', '${el[2]}', null);`
              })
              .forEach(stmt => {
                conn.execute(stmt, (err, res) => {
                  if (err) {
                    console.error('Execute error', err)
                  } else {
                    console.log('Results: ', res)
                  }

                  conn.release()
                })
              })
          },
        )

        conn.release()
      })
    })
    .on('end', () => {
      setTimeout(() => {
        process.exit(0)
      }, 60000)
    })
}

run()
