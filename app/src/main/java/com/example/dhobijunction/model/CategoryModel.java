package com.example.dhobijunction.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryModel {

        String cId;

        String title;

        public String getcId() {
                return cId;
        }

        public void setcId(String cId) {
                this.cId = cId;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }
}
