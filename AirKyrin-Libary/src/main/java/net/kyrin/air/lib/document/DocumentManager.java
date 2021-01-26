package net.kyrin.air.lib.document;

import net.kyrin.air.lib.document.models.Document;
import net.kyrin.air.lib.document.models.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DocumentManager {

    public static String getString(Document document, String key) {
        return Objects.requireNonNull(document.getEntriesList().stream().filter(entry -> (entry.getKey().equalsIgnoreCase(key))).findFirst().orElse(Entry.newBuilder().setValue("").build())).getValue();
    }

    public static Integer getInteger(Document document, String key) {
        return Integer.valueOf(getString(document, key));
    }

    public static Long getLong(Document document, String key) {
        return Long.valueOf(getString(document, key));
    }

    public static Boolean getBoolean(Document document, String key) {
        return Boolean.valueOf(getString(document, key));
    }

    public static Double getDouble(Document document, String key) {
        return Double.valueOf(getString(document, key));
    }

    public static UUID getUUID(Document document, String key){
        return UUID.fromString(getString(document, key));
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static class Builder{

        private List<Entry> entries = new ArrayList<>();

        private Builder() {}

        public Builder setString(String key, String value){
            entries.add(Entry.newBuilder().setKey(key).setValue(value).build());
            return this;
        }

        public Builder setInteger(String key, Integer value){
            return setString(key, value.toString());
        }

        public Builder setLong(String key, Long value){
            return setString(key, value.toString());
        }

        public Builder setBoolean(String key, Boolean value){
            return setString(key, value.toString());
        }

        public Builder setDouble(String key, Double value){
            return setString(key, value.toString());
        }

        public Builder setUUID(String key, UUID uuid){
            return setString(key, uuid.toString());
        }

        public Document build(){
            return Document.newBuilder().addAllEntries(entries).build();
        }
    }

}
