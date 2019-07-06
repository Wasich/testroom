package com.example.testroom;

public class Repo {


        int id;
        String name;
        String description;
        double price;
        String thumbnail;
        String chef;
        String timestamp;

        public Repo(int id, String name, String description, double price, String thumbnail, String chef, String timestamp) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.thumbnail = thumbnail;
            this.chef = chef;
            this.timestamp = timestamp;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getChef() {
            return chef;
        }

        public void setChef(String chef) {
            this.chef = chef;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

