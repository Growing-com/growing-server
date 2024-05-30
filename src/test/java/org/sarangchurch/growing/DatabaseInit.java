package org.sarangchurch.growing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
@Profile("test")
public class DatabaseInit {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/data.dump.sql"));

            String line;
            StringBuilder sqlStringbuilder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                // Ignore comments and empty lines
                if (!line.trim().startsWith("--") && !line.trim().isEmpty()) {
                    sqlStringbuilder.append(line);

                    // If the line ends with a semicolon, execute the SQL statement
                    if (line.trim().endsWith(";")) {
                        String sql = sqlStringbuilder.toString();
                        jdbcTemplate.execute(sql);
                        sqlStringbuilder.setLength(0); // Clear the SQL StringBuilder
                    }
                }
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
