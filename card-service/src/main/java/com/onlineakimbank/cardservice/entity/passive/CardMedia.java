package com.onlineakimbank.cardservice.entity.passive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("card_media")
public class CardMedia{

    @PrimaryKey
    private String cardId;

    private String frontImageUrl;
    private String backImageUrl;
    private String stickerImageUrl;

}
