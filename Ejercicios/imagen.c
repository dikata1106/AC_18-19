#include <stdio.h>

struct Imagen {
	int h, w;
	unsigned char pix[16][16];
}img;

void  initArray() {

}

int main(int argc, char const *argv[]) {

	FILE *fp1;
	fp1 = fopen("imagenes1.dat","r");
	FILE *fp2;
	fp2 = fopen("imagenes2.dat","r");


	int N;
	fscanf(fp1, "%d", &N);
	struct Imagen img1[N];
	fscanf(fp2, "%d", &N);
	struct Imagen img2[N];


	int i, h, w;
	for(i = 0; i < N; i++) {
		fscanf(fp1, "%d %d", &img1[i].h, &img1[i].w);
		int x, y;
		for(x = 0; x < img1[i].h; x++) {
			for(y = 0; y < img1[i].w; y++) {
				fscanf(fp1, "%c", &img1[i].pix[x][y]);
			}
		}
	}

	for(i = 0; i < N; i++) {
		fscanf(fp2, "%d %d", &img2[i].h, &img2[i].w);
		int x, y;
		for(x = 0; x < img2[i].h; x++) {
			for(y = 0; y < img2[i].w; y++) {
				fscanf(fp2, "%c", &img2[i].pix[x][y]);
			}
		}
	}

	i = 0;
	int igual = 1;
	while(i < N) {
		if(img1[i].h == img2[i].h && img1[i].w == img2[i].w) {
			for(h = 0; h < img1[i].h; h++) {
				for(w = 0; w < img1[i].w; w++) {
					if(img1[i].pix[h][w] != img2[i].pix[h][w]) {
						igual = 0;
						break;
					}
					if(igual == 0) {
						break;
					}
				}
			}
		} else {
			igual = 0;
		}
		if(igual == 0) {
			break;
		}
		i++;
	}

	fclose(fp1);
	fclose(fp2);

	if(igual == 1){
		printf("Son iguales\n");
	}else{
		printf("No son iguales\n");
	}
}
