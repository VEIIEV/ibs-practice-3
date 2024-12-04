package oldTest;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class FailedTestLoggerExtension implements TestWatcher {


    private static final Logger logger = LoggerFactory.getLogger("org.junit.jupiter");

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getDisplayName();
        String errorMessage = Optional.ofNullable(cause.getMessage()).orElse("Без сообщения");




        logger.error("Тест \"{}\" провалился: {}", testName, errorMessage);
    }
}