package ru.netology.cloudservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudservice.exceptions.CloudExceptionHandlerAdvice;
import ru.netology.cloudservice.exceptions.FileInternalServerException;
import ru.netology.cloudservice.exceptions.FileNotFoundException;
import ru.netology.cloudservice.model.Item;
import ru.netology.cloudservice.dto.ResultMessageDto;
import ru.netology.cloudservice.repository.ItemRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CloudExceptionHandlerAdvice
public class FileServiceImpl implements FileService {
    private final AuthService authService;
    private final ItemRepository itemRepository;

    @Override
    public List<Item.FileDto> getItemList(Optional<Integer> limit, Optional<String> token) {
        authService.validateToken(token);
        String owner = authService.getUsernameByToken(token);
        List<Item> items = itemRepository.findItemByOwnerOrderByFileSizeAsc(owner, limit.map(Limit::of).orElseGet(Limit::unlimited));
        return items.stream().map(Item::getFile).toList();
    }

    @Override
    public Object downloadItem(String filename, Optional<String> token) {
        authService.validateToken(token);
        String owner = authService.getUsernameByToken(token);
        if (itemRepository.findItemByOwnerAndFileFilename(owner, filename).isEmpty())
            throw new FileNotFoundException(Operations.FILE_DOWNLOAD, filename);
        Optional<Item> item = itemRepository.findItemByOwnerAndFileFilename(owner, filename);
        return item.get().getData();
    }

    @Override
    public ResultMessageDto saveItem(String filename, MultipartFile file, Optional<String> token) {
        authService.validateToken(token);
        String owner = authService.getUsernameByToken(token);
        try {
            int rows = itemRepository.saveItemToDatabase(
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.hashCode(),
                    owner,
                    file.getInputStream().readAllBytes());
            if (rows <= 0) {
                throw new FileInternalServerException(Operations.FILE_UPLOAD, file.getOriginalFilename());
            }
        } catch (IOException e) {
            throw new FileInternalServerException(Operations.FILE_UPLOAD, file.getOriginalFilename());
        }
        return new ResultMessageDto("File upload success, file: " + filename);
    }

    @Override
    public ResultMessageDto updateItem(String oldFilename, String newFilename, Optional<String> token) {
        authService.validateToken(token);
        String owner = authService.getUsernameByToken(token);
        Optional<Item> item = itemRepository.findItemByOwnerAndFileFilename(owner, oldFilename);
        if (item.isEmpty())
            throw new FileNotFoundException(Operations.FILE_UPDATE, oldFilename);
        item.get().getFile().setFilename(newFilename);
        itemRepository.save(item.get());
        return new ResultMessageDto("File update success, new filename: " + newFilename);
    }

    @Override
    public ResultMessageDto deleteItem(String filename, Optional<String> token) {
        authService.validateToken(token);
        String owner = authService.getUsernameByToken(token);
        Optional<Item> item = itemRepository.findItemByOwnerAndFileFilename(owner, filename);
        if (item.isEmpty())
            throw new FileNotFoundException(Operations.FILE_DELETE, filename);
        item.ifPresent(itemRepository::delete);
        return new ResultMessageDto("File delete success, file: " + filename);
    }
}
