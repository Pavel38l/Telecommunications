package ru.vsu.telecom.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author Pavel_Burdyug
 */

@Builder
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelPackage that = (ChannelPackage) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
