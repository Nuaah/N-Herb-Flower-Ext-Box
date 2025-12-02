package com.Nuaah.NHerbFlowerExtBox.regi;

import java.util.List;

public class ConstituentsData {
    public List<ComponentData> components;
    public List<Float> color;

    public static class ComponentData {
        public String soluble;
        public String part;
        public String id;
        public float amount;
        public int duration;
    }
}
