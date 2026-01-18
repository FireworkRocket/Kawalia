package top.fireworkrocket.kawalia.exception.implement;

import com.sun.management.HotSpotDiagnosticMXBean;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.ExtendedLogger;
import top.fireworkrocket.kawalia.exception.api.HeapDump;
import top.fireworkrocket.kawalia.i18n.api.Messages;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HeapDumpService implements HeapDump {
    private static final String WRAPPER_FQCN = HeapDumpService.class.getName();
    private static final Logger LOGGER = LogManager.getLogger(HeapDumpService.class);
    private static final DateTimeFormatter HEAP_DUMP_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private final Messages messages;

    public HeapDumpService(Messages messages) {
        this.messages = messages;
    }

    private void log(String message, Throwable t) {
        if (LOGGER instanceof ExtendedLogger) {
            ((ExtendedLogger) LOGGER).logIfEnabled(WRAPPER_FQCN, Level.FATAL, null, message, t);
        } else {
            LOGGER.log(Level.FATAL, message, t);
        }
    }

    @Override
    public void createHeapDump() {
        try {
            String timestamp = LocalDateTime.now().format(HEAP_DUMP_TIMESTAMP_FORMATTER);
            String dumpFileName = "heap_dump_" + timestamp + ".hprof";
            dump(dumpFileName);
            String successMsg = messages.get("system.info.log.heapDump.success", dumpFileName);
            log(successMsg, null);
        } catch (Exception heapDumpException) {
            String failureMsg = messages.get("system.error.log.heapDump.failure");
            log(failureMsg, heapDumpException);
        }
    }

    private void dump(String filePath) throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        HotSpotDiagnosticMXBean bean = ManagementFactory.newPlatformMXBeanProxy(
                server,
                "com.sun.management:type=HotSpotDiagnostic",
                HotSpotDiagnosticMXBean.class);
        bean.dumpHeap(filePath, true);
    }
}
