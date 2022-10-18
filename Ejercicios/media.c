#include <stdio.h>

float media(int *V, int n) {
    int i;
    int suma = 0;
    for(i = 0; i < n; i++) {
        suma = suma + V[i];
    }
    float mda = (float) suma / n;
    return (mda);
}

void main() {

    int n = 5;
    int V[n];
    float M[n/2][n/2];

    printf("\nValores del vector de %d elementos: \n", n);
    int i;
    for(i = 0; i < n; i++) {
        scanf("%d", &V[i]);
    }

    printf("\nValores de la matrix %dx%d elementos: \n", n/2, n/2);
    int j;
    for(i = 0; i < n/2; i++) {
        for(j = 0; j < n/2; j++) {
            scanf("%f", &M[i][j]);
        }
    }

    float mda = media(V, n);

    for(i = 0; i < n/2; i++) {
        for(j = 0; j < n/2; j++) {
            M[i][j] = M[i][j] + mda;
        }
    }

    printf("\n Resultado: \n");
    for(i = 0; i < n/2; i++) {
        printf("\n");
        for(j = 0; j < n/2; j++) {
            printf("%.2f   ", M[i][j]);
        }
    }
    printf("\n \n");

}