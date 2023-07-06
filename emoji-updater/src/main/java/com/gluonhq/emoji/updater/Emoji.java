package com.gluonhq.emoji.updater;

import java.util.List;

public class Emoji {

    private static final String COMMA_DELIMITER = "#";
    private static final String ITEMS_LIST_DELIMITER = "!";
    private static final String FIELDS_SKIN_DELIMITER = ",";

    private String name;
    private String unified;
    private String non_qualified;

    private String docomo;
    private String au;
    private String softbank;
    private String google;
    private String image;

    private int sheet_x;
    private int sheet_y;

    private String short_name;
    private List<String> short_names;

    private String text;
    private List<String> texts;

    private String category;
    private String subcategory;
    private int sort_order;
    private String added_in;

    private boolean has_img_apple;
    private boolean has_img_google;
    private boolean has_img_twitter;
    private boolean has_img_facebook;

    private List<Emoji> skin_variations;

    private String obsoletes;
    private String obsoleted_by;

    public Emoji() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnified() {
        return unified;
    }

    public void setUnified(String unified) {
        this.unified = unified;
    }

    public String getNon_qualified() {
        return non_qualified;
    }

    public void setNon_qualified(String non_qualified) {
        this.non_qualified = non_qualified;
    }

    public String getDocomo() {
        return docomo;
    }

    public void setDocomo(String docomo) {
        this.docomo = docomo;
    }

    public String getAu() {
        return au;
    }

    public void setAu(String au) {
        this.au = au;
    }

    public String getSoftbank() {
        return softbank;
    }

    public void setSoftbank(String softbank) {
        this.softbank = softbank;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSheet_x() {
        return sheet_x;
    }

    public void setSheet_x(int sheet_x) {
        this.sheet_x = sheet_x;
    }

    public int getSheet_y() {
        return sheet_y;
    }

    public void setSheet_y(int sheet_y) {
        this.sheet_y = sheet_y;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public List<String> getShort_names() {
        return short_names;
    }

    public void setShort_names(List<String> short_names) {
        this.short_names = short_names;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getAdded_in() {
        return added_in;
    }

    public void setAdded_in(String added_in) {
        this.added_in = added_in;
    }

    public boolean isHas_img_apple() {
        return has_img_apple;
    }

    public void setHas_img_apple(boolean has_img_apple) {
        this.has_img_apple = has_img_apple;
    }

    public boolean isHas_img_google() {
        return has_img_google;
    }

    public void setHas_img_google(boolean has_img_google) {
        this.has_img_google = has_img_google;
    }

    public boolean isHas_img_twitter() {
        return has_img_twitter;
    }

    public void setHas_img_twitter(boolean has_img_twitter) {
        this.has_img_twitter = has_img_twitter;
    }

    public boolean isHas_img_facebook() {
        return has_img_facebook;
    }

    public void setHas_img_facebook(boolean has_img_facebook) {
        this.has_img_facebook = has_img_facebook;
    }

    public List<Emoji> getSkin_variations() {
        return skin_variations;
    }

    public void setSkin_variations(List<Emoji> skin_variations) {
        this.skin_variations = skin_variations;
    }

    public String getObsoletes() {
        return obsoletes;
    }

    public void setObsoletes(String obsoletes) {
        this.obsoletes = obsoletes;
    }

    public String getObsoleted_by() {
        return obsoleted_by;
    }

    public void setObsoleted_by(String obsoleted_by) {
        this.obsoleted_by = obsoleted_by;
    }

    @Override
    public String toString() {
        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, unified);
        appendFieldValue(dataBuilder, non_qualified);
        appendFieldValue(dataBuilder, docomo);
        appendFieldValue(dataBuilder, au);
        appendFieldValue(dataBuilder, softbank);
        appendFieldValue(dataBuilder, google);
        appendFieldValue(dataBuilder, image);
        appendFieldValue(dataBuilder, String.valueOf(sheet_x));
        appendFieldValue(dataBuilder, String.valueOf(sheet_y));
        appendFieldValue(dataBuilder, short_name);
        appendFieldValue(dataBuilder, short_names);
        appendFieldValue(dataBuilder, text);
        appendFieldValue(dataBuilder, texts);
        appendFieldValue(dataBuilder, category);
        appendFieldValue(dataBuilder, subcategory);
        appendFieldValue(dataBuilder, String.valueOf(sort_order));
        appendFieldValue(dataBuilder, added_in);
        appendFieldValue(dataBuilder, String.valueOf(has_img_apple));
        appendFieldValue(dataBuilder, String.valueOf(has_img_google));
        appendFieldValue(dataBuilder, String.valueOf(has_img_twitter));
        appendFieldValue(dataBuilder, String.valueOf(has_img_facebook));
        appendFieldValue(dataBuilder, skin_variations);
        appendFieldValue(dataBuilder, obsoletes);
        appendFieldValue(dataBuilder, obsoleted_by);
        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if (fieldValue != null) {
            dataBuilder.append(fieldValue).append(COMMA_DELIMITER);
        } else {
            dataBuilder.append(COMMA_DELIMITER);
        }
    }

    private <T> void appendFieldValue(StringBuilder dataBuilder, List<T> fieldValue) {
        if (fieldValue != null) {
            for (T t : fieldValue) {
                if (t instanceof Emoji) {
                    dataBuilder.append(skinValueToString((Emoji) t)).append(ITEMS_LIST_DELIMITER);
                } else {
                    dataBuilder.append(t.toString()).append(ITEMS_LIST_DELIMITER);
                }
            }
            dataBuilder.append(COMMA_DELIMITER);
        } else {
            dataBuilder.append(COMMA_DELIMITER);
        }
    }

    private String skinValueToString(Emoji skinVariation) {
        StringBuilder dataBuilder = new StringBuilder();
        appendSkinFieldValue(dataBuilder, skinVariation.name);
        appendSkinFieldValue(dataBuilder, skinVariation.unified);
        appendSkinFieldValue(dataBuilder, skinVariation.non_qualified);
        appendSkinFieldValue(dataBuilder, skinVariation.image);
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.sheet_x));
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.sheet_y));
        appendSkinFieldValue(dataBuilder, skinVariation.added_in);
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.has_img_apple));
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.has_img_google));
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.has_img_twitter));
        appendSkinFieldValue(dataBuilder, String.valueOf(skinVariation.has_img_facebook));
        appendSkinFieldValue(dataBuilder, skinVariation.obsoletes);
        appendSkinFieldValue(dataBuilder, skinVariation.obsoleted_by);
        return dataBuilder.toString();
    }

    private void appendSkinFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if (fieldValue != null) {
            dataBuilder.append(fieldValue).append(FIELDS_SKIN_DELIMITER);
        } else {
            dataBuilder.append(FIELDS_SKIN_DELIMITER);
        }
    }
}
