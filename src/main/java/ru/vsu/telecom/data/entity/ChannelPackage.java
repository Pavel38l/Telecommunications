package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pavel_Burdyug
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChannelPackage {
    private Long id;
    private String name;
    private String description;

    public ChannelPackage(String name, String description) {
        this(null, name, description);
    }
}
