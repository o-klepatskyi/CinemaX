package ua.edu.ukma.cinemax.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class TicketStats {
    @Value("${stat.src}")
    private String path;
    private Tickets tickets = new Tickets();

    private final ObjectMapper objectMapper;

    public TicketStats() {
        objectMapper = new ObjectMapper();
    }

    private File getFile() throws IOException {
        return ResourceUtils.getFile(path);
    }

    public int totalTicketsBought() {
        return tickets.getTotalBought();
    }

    public void updateData() {
        try {
            tickets = objectMapper.readValue(getFile(), Tickets.class);
        } catch (IOException e) {
            System.err.println("Statistics were not updated");
        }
    }

    // increments ticket count
    public void update(int value) {
        try {
            Tickets s = objectMapper.readValue(getFile(), Tickets.class);
            s.setTotalBought(s.getTotalBought() + value);
            objectMapper.writeValue(new FileOutputStream(getFile().getPath()), s);
        } catch(IOException e) {
            throw new RuntimeException("File was not opened", e);
        }
    }
}
