package msku.ceng3545.hobbyplanner;
import org.junit.Test;
import static org.junit.Assert.*;


import msku.ceng3545.hobbyplanner.models.EventModel;
public class EventModelTest {
    @Test
    public void eventModel_LogicIsCorrect() {
        // 1. Hazırlık (Setup)
        // Örnek: Başlık "Yürüyüş", Detay "Sahil", Kategori "Spor", Mevcut 5, Max 10
        EventModel event = new EventModel("Yürüyüş", "Sahil", "Spor", 5, 10);

        // 2. Kontrol (Assertion) - Beklenen vs Gerçekleşen

        // Başlık doğru kaydedilmiş mi?
        assertEquals("Yürüyüş", event.getTitle());

        // Mevcut katılımcı sayısı doğru mu?
        assertEquals(5, event.getCurrent());

        // Bizim yazdığımız getStatusText() fonksiyonu "5 / 10" döndürüyor mu?
        assertEquals("5 / 10", event.getStatusText());
    }
}

