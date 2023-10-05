package monitoring_ozone.constants;

public interface MailConstants {

    String MAIL_SUBJECT_FOR_REMEMBER_PASSWORD = "Восстановление пароля";

    String MAIL_MESSAGE_FOR_REMEMBER_PASSWORD = """
            Добрый день.
            Для восстановления пароля перейдите по ссылке:
            http://localhost:8080/users/change-password?uuid=
            """;
}
