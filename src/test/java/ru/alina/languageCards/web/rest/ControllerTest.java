package ru.alina.languageCards.web.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import ru.alina.languageCards.TimingExtension;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Sql(scripts = {"classpath:static/db/initPostgres.sql", "classpath:static/db/populateTest.sql"}, config = @SqlConfig(encoding = "UTF-8"), executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith(TimingExtension.class)
public abstract class ControllerTest {
}
