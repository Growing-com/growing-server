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

const generateObjectId = (): string => {
  const characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'

  let randomObjectId = ''

  for (let i = 0; i < 10; i++) {
    const randomIndex = Math.floor(Math.random() * characters.length)

    randomObjectId += characters.charAt(randomIndex)
  }

  return randomObjectId
}

const run = async () => {
  fs.createReadStream('src/scripts/growing/1_users.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.execute(
          // 1. 등록
          `insert ignore into users (created_at, created_by, updated_at, updated_by, username, password, role, name, phone_number, sex, birth, grade, is_active, visit_date, visit_term_id)
                  values ('2023-09-01', null, '2023-09-01', null, '${generateObjectId()}', '${generateObjectId()}', 'NORMAL', ?, ?, ?, ?, ?, 1, '1970-01-01', 0)`,
          [row.name, row.phone, row.sex === '남' ? 'MALE' : 'FEMALE', row.birth, row.grade],
          (err, results, fields) => {
            if (err) {
              console.error('Execute error', err)
            } else {
              console.log('Results: ', results)
            }

            conn.release()
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
