package monitoring_ozone.constants;

import java.util.List;

public interface EmptyProductName {

    String ENDED = "Этот товар закончился";

    String NOT_DELIVERED = "Товар не доставляется в ваш регион";

    List<String> ALL = List.of(ENDED, NOT_DELIVERED);
}
