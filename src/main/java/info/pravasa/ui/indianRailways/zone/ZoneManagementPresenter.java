package info.pravasa.ui.indianRailways.zone;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.ZoneDto;
import info.pravasa.services.indianRailServices.ZoneService;

import java.util.List;

@UIScope
@SpringComponent
public class ZoneManagementPresenter {

    private final ZoneService zoneService;

    public ZoneManagementPresenter(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public List<ZoneDto> fetchAllZones() {
        return zoneService.fetchAllZones();
    }

    public ZoneDto save(ZoneDto zoneDto) {
        return zoneService.save(zoneDto);
    }

    public void delete(Long id) {
        zoneService.delete(id);
    }
}
