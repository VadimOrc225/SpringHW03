package ru.gb.springbootlesson3.repository;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.gb.springbootlesson3.entity.Reader;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class ReaderRepository {
    private final List<Reader> readersList = new ArrayList<>();

    {
        readersList.add(new Reader("Олег"));
        readersList.add(new Reader("Евгений"));
        readersList.add(new Reader("Вадим"));
        readersList.add(new Reader("Светлана"));
        readersList.add(new Reader("Александр"));
    }

    public Reader getById(long id){
        return readersList.stream().filter(reader -> reader.getId() == id).findFirst().orElse(null);
    }
    public void addReader(Reader reader){
        readersList.add(reader);
    }
    public List<Reader> getAllReaders(){
        return readersList;
    }
    public boolean deleteReader(Reader reader){
        return readersList.removeIf(b -> b.equals(reader));
    }
}
