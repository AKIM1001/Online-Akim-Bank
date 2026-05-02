package com.onlineakimbank.cardservice.service;

import com.onlineakimbank.cardservice.entity.passive.CardMedia;
import com.onlineakimbank.cardservice.repository.CardMediaRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class CardMediaService {

    private final CardMediaRepository cardMediaRepository;
    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Transactional
    public String uploadFrontImage(String cardId, MultipartFile frontImageFile) {
        String filePath = buildFilePath("frontImages", cardId, frontImageFile);
        uploadFileToMinio(filePath, frontImageFile);

        return updateCardMedia(cardId, mediaEntity -> mediaEntity
                .setFrontImageUrl("/" + bucketName + "/" + filePath))
                .getFrontImageUrl();
    }

    public String getFrontImageUrl(String cardId) {
        CardMedia mediaEntity = getCardMedia(cardId);
        if (mediaEntity == null || mediaEntity.getFrontImageUrl() == null) {
            throw new IllegalArgumentException("[FrontImage not found for user: " + cardId + "]");
        }
        return mediaEntity.getFrontImageUrl();
    }

    @Transactional
    public String uploadBackImage(String cardId, MultipartFile backImageFile) {
        String filePath = buildFilePath("backImages", cardId, backImageFile);
        uploadFileToMinio(filePath, backImageFile);

        return updateCardMedia(cardId, mediaEntity -> mediaEntity
                .setBackImageUrl("/" + bucketName + "/" + filePath))
                .getBackImageUrl();
    }

    public String getBackImageUrl(String cardId) {
        CardMedia mediaEntity = getCardMedia(cardId);
        if (mediaEntity == null || mediaEntity.getBackImageUrl() == null) {
            throw new IllegalArgumentException("[BackImage not found for user: " + cardId + "]");
        }
        return mediaEntity.getBackImageUrl();
    }

    @Transactional
    public String uploadStickerImage(String cardId, MultipartFile stickerImageFile) {
        String filePath = buildFilePath("stickerImages", cardId, stickerImageFile);
        uploadFileToMinio(filePath, stickerImageFile);

        return updateCardMedia(cardId, mediaEntity -> mediaEntity
                .setStickerImageUrl("/" + bucketName + "/" + filePath))
                .getStickerImageUrl();
    }

    public String getStickerImageUrl(String cardId) {
        CardMedia mediaEntity = getCardMedia(cardId);
        if (mediaEntity == null || mediaEntity.getStickerImageUrl() == null) {
            throw new IllegalArgumentException("[StickerImage not found for user: " + cardId + "]");
        }
        return mediaEntity.getStickerImageUrl();
    }

    private void uploadFileToMinio(String filePath, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filePath)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (MinioException e) {
            throw new RuntimeException("[Failed to upload file to MinIO: " + e.getMessage() + "]", e);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("[File upload error: " + e.getMessage() + "]", e);
        }
    }

    private String buildFilePath(String type, String cardId, MultipartFile file) {
        if (file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("[File must have a name]");
        }
        return String.format("%s/%s/%s", type, cardId, file.getOriginalFilename());
    }

    private CardMedia updateCardMedia(String cardId, java.util.function.Consumer<CardMedia> updater) {
        CardMedia mediaEntity = cardMediaRepository.findByCardId(cardId);

        if (mediaEntity == null) {
            mediaEntity = new CardMedia();
        }

        updater.accept(mediaEntity);
        mediaEntity.setCardId(cardId);

        return cardMediaRepository.save(mediaEntity);
    }

    private CardMedia getCardMedia(String cardId) {
        return cardMediaRepository.findByCardId(cardId);
    }
}
