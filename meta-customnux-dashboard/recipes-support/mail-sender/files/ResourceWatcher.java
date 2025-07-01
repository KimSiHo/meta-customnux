import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ResourceWatcher {

    public static double getCpuUsage() {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "top -bn1 | grep '%Cpu' | awk '{print $2}'");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            process.waitFor();
            return Double.parseDouble(line.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static double getMemoryUsage() {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "free | grep '메모리' | awk '{print ($3/$2) * 100.0}'");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            process.waitFor();
            return Double.parseDouble(line.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public static void sendEmail(String username, String password, String fromAddress, String toAddress, String subject, String body) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.debug", "true");

        // 세션 생성
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // 메일 구성
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toAddress)
            );
            message.setSubject(subject);
            message.setText(body);

            // 메일 전송
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final String username = "odreamgodo@gmail.com";
        final String password = "umjz enpf rmsp jede";
        final String fromAddress = "kim125y@naver.com";
        final String toAddress = "kim125y@naver.com";

        // 테스트용 메일 보내기
        String testSubject = "Test Mail from Java";
        String testBody = "Hello, this is a test email sent from a Java program!";
        //sendEmail(username, password, fromAddress, toAddress, testSubject, testBody);

        double cpuUsage = getCpuUsage();
        double memoryUsage = getMemoryUsage();
        System.out.println("CPU Usage: " + cpuUsage + "%");
        System.out.println("Memory Usage: " + memoryUsage + "%");

        if (cpuUsage > 1.0 && memoryUsage > 20.0) {
            String alertSubject = "Alert: High Resource Usage";
            String alertBody = "CPU Usage: " + cpuUsage + "%\nMemory Usage: " + memoryUsage + "%\n\nThis is an automated alert for high resource usage.";
            sendEmail(username, password, fromAddress, toAddress, alertSubject, alertBody);
        }
    }
}
