#!/usr/bin/env python
# -*- coding: utf-8 -*-
# Copyright (c) 2014-18 Richard Hull and contributors
# See LICENSE.rst for details.
# PYTHON_ARGCOMPLETE_OK

"""
Greyscale rendering demo.
"""

import time
import os.path
from demo_opts import get_device
from luma.oled.device import ssd1306
from luma.core.interface.serial import i2c
from luma.core.render import canvas
from PIL import Image



def displayImage(image):
    serial = i2c(port=1, address=0x3c)
    device = ssd1306(serial, rotate=0)

    if image == "CLEAR":
        device.clear()
    else:
        img_path = os.path.abspath(os.path.join(os.path.dirname(__file__),
                'images', image+".png"))
        if not os.path.isfile(img_path):
            img_path = os.path.abspath(os.path.join(os.path.dirname(__file__),
                'images', image+".jpg"))

        someImage = Image.open(img_path) \
            .transform(device.size, Image.AFFINE, (1, 0, 0, 0, 1, 0), Image.NEAREST) \
            .convert("L") \
            .convert(device.mode)
        someImage.resize((128,64))

        device.display(someImage)
        # time.sleep(10)

if __name__ == '__main__':
    displayImage()
