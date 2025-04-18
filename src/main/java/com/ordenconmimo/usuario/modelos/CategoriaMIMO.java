package com.ordenconmimo.usuario.modelos;

    public enum CategoriaMIMO {
        MIRATE("Mírate"),
        IMAGINA("Imagina"),
        MUEVETE("Muévete"),
        ORDENA("Ordena");
        
        private final String displayName;
        
        CategoriaMIMO(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
    