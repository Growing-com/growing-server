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
  fs.createReadStream('src/scripts/growing/10_birth_and_vdate.csv')
    .pipe(csv())
    .on('data', row => {
      pool.getConnection((err, conn) => {
        if (err) {
          process.exit(-1)
        }

        conn.query(
          `select id from users where name = ? and grade = ? and sex = ?`,
          [row.name, row.grade, row.sex === '남' ? 'MALE' : 'FEMALE'],
          (err, result1) => {
            // @ts-ignore
            if (result1[0] === undefined) {
              console.log(`${row.name} is not present`)

              conn.release()

              return
            }

            // @ts-ignore
            conn.execute(
              `update users set birth = ?, visit_date = ? where id = ?`,
              // @ts-ignore
              [row.birth, row.visitDate, result1[0].id],
              (err, result2) => {
                if (err) {
                  console.error(err)
                } else {
                  console.log('Success')
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
