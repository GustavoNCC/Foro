package Foro.Perfil;

public final class Validacion {

    private Validacion() {
        // Constructor privado para evitar instanciación
    }

    public static boolean esEmailValido(String correo) {
        if (correo == null || correo.isBlank()) return false;

        int arroba = correo.indexOf("@");
        int punto = correo.lastIndexOf(".");

        return arroba > 0 && punto > arroba + 1 && punto < correo.length() - 1;
    }

    public static boolean esContrasenaSegura(String clave) {
        if (clave == null || clave.length() < 8) return false;

        int digitos = 0;
        boolean tieneEspecial = false;

        for (char caracter : clave.toCharArray()) {
            if (Character.isDigit(caracter)) digitos++;
            if ("@#$".indexOf(caracter) != -1) tieneEspecial = true;
        }

        return digitos >= 2 && tieneEspecial;
    }
}
