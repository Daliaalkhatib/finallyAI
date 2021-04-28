package com.example.spamfilteringusingai_2;

public class WeatherSample {


        private String email;
        private  int label ;




        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return "WeatherSample{" +
                    "email='" + email + '\'' +
                    ", label=" + label +
                    '}';
        }
    }


