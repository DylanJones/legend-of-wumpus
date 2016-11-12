from PIL import Image

w = 307
h = 137

everything = Image.open('overworldtiles.png')

for x in range(1, w, 17):
    for y in range(1, h, 17):
        newimg = everything.crop((x, y, x+16, y+16))
        newimg.save(str(int((x - 1) / 17)) + "," + str(int((y - 1) / 17)) + ".png")
