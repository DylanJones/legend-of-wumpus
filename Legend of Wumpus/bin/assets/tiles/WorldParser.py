#!/usr/bin/env python3
from PIL import Image
import os

ids = open('overworld/id','r').read().split('\n')
ids = [(id.split()[0], id.split()[1]) for id in ids if len(id.split()) == 2]
tiles = [(Image.open('overworld/' + id[0] + '.png'), id[1]) for id in ids]

to_parse = [f for f in os.listdir('templates') if os.path.isfile(os.path.join('templates', f))]

def equals(img1, img2):
    if img1.height != img2.height or img1.width != img2.width:
        print('unequal size')
        return False
    for x in range(img1.width):
        for y in range(img1.height):
            if img1.getpixel((x,y)) != img2.getpixel((x,y)):
                return False
    return True

def findCode(img):
    for tile in tiles:
        if equals(img, tile[0]):
            return tile[1]
    return '1A' #shore_ne tile

print('Parsing Tiles...')
for file in to_parse:
    img = Image.open('templates/' + file).resize((256, 168)).convert('RGB')
    out_file = open('worlds/tiles/' + file.replace('.gif', '') + '.tiles', 'w')
    print('Working on', out_file.name)
    for y in range(0, img.height, 16):
        for x in range(0,img.width, 16):
            new_img = img.crop((x, y, x+16, y+16))
            code = findCode(new_img)
            out_file.write(code + " ")
        out_file.write("\n")
    out_file.close()

print('Creating World Files...')
letters = 'ABCDEFGHIJKLMNOP'
numbers = '12345678'

for x in range(len(letters)):
    for y in range(len(numbers)):
        with open('worlds/' + numbers[y] + letters[x] + '.wld','w') as file:
            file.write('tiles ' + numbers[y] + letters[x] + '.tiles\n')
            file.write('music Overworld.wav\n')
            file.write('north ' + numbers[(y + 1) % len(numbers)] + letters[x] + '.wld\n')
            file.write('south ' + numbers[y - 1 if y - 1 >= 0 else len(numbers) - 1] + letters[x] + '.wld\n')
            file.write('east ' + numbers[y] + letters[(x + 1) %  len(letters)] + '.wld\n')
            file.write('west ' + numbers[y] + letters[x - 1 if x - 1 >= 0 else len(letters) - 1] + '.wld\n')


