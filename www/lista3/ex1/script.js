"use strict";
/* global Prism */
/* jslint browser: true, devel: true, sloppy: false */

// HAMBUGER MENU: toggle nav on small screens
(function () {
    document.addEventListener('DOMContentLoaded', function () {
        var toggle = document.getElementById('menu-toggle');
        var nav = document.querySelector('header nav ul');

        if (toggle && nav) {
            toggle.addEventListener('click', function () {
                nav.classList.toggle('open');
            });
        }

        // DYNAMIC GALLERY LOADING WITH PROMISES
        var galleryContainer = document.querySelector('.gallery');
        if (galleryContainer) {
            // list of image sources and alts
            var images = [
                { src: 'IMG_3305.jpg', alt: 'Zdjęcie 1' },
                { src: 'IMG_1305.jpg', alt: 'Zdjęcie 2' },
                { src: 'IMG_1239.jpg', alt: 'Zdjęcie 3' },
                { src: 'IMG_1235.jpg', alt: 'Zdjęcie 4' },
                { src: 'IMG_1333.jpg', alt: 'Zdjęcie 5' },
                { src: 'IMG_0907.jpg', alt: 'Zdjęcie 6' }
            ];

            function loadImage(item) {
                return new Promise(function (resolve, reject) {
                    var img = new Image();
                    img.src = item.src;
                    img.alt = item.alt;
                    img.onload = function () { resolve(img); };
                    img.onerror = function () { reject(new Error('Failed to load ' + item.src)); };
                });
            }

            Promise.all(images.map(loadImage))
                .then(function (loaded) {
                    loaded.forEach(function (img) {
                        galleryContainer.appendChild(img);
                    });
                })
                .catch(function (err) {
                    console.error(err);
                });
        }

        // Prism syntax highlighting initialization
        if (window.Prism) {
            Prism.highlightAll();
        }
    });
}());
