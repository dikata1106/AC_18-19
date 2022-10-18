#include <stdio.h>

struct socio {
    char nombre[40];
    int edad;
    int num_socio;
};

void main() {
    int n = 527;
    struct socio socios[n];

    FILE *fp;
    fp = fopen("socios.dat", "r");

    int i;
    for(i = 0; i < n; i++) {
        fscanf(fp, "%s", &socios[i].nombre);
        fscanf(fp, "%d", &socios[i].edad);
        fscanf(fp, "%d", &socios[i].num_socio);
    }
    fclose(fp);

    int min = socios[0].edad;
    int max = min;
    for(i = 1; i < n; i++) {
        if(socios[i].edad < min) {
            min = socios[i].edad;
        } else if(socios[i].edad > max) {
            max = socios[i].edad;
        }
    }

    FILE *joven = fopen("jovenes.dat", "a");
    FILE *mayor = fopen("mayores.dat", "a");
    FILE *jubilado = fopen("juvilados.dat", "a");

    for(i = 0; i < n; i++) {
        if(socios[i].edad == min) {
            fprintf(joven, "%s %d %d \n", socios[i].nombre, socios[i].edad, socios[i].num_socio);  
        } else if(socios[i].edad == max) {
            fprintf(mayor, "%s %d %d \n", socios[i].nombre, socios[i].edad, socios[i].num_socio);  
        }
        if(socios[i].edad >= 65) {
            fprintf(jubilado, "%s %d %d \n", socios[i].nombre, socios[i].edad, socios[i].num_socio);  
        }
    }
    fclose(joven);
    fclose(mayor);
    fclose(jubilado);

}