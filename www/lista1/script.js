"use strict";

document.addEventListener("DOMContentLoaded", function () {
  var toggle = document.querySelector(".nav-toggle");
  var menu = document.querySelector(".nav-menu");
  var images = [
    "https://picsum.photos/300/200?random=1",
    "https://picsum.photos/300/200?random=2",
    "https://picsum.photos/300/200?random=3",
    "https://picsum.photos/300/200?random=4"
  ];
  var gallery = document.getElementById("gallery-container");

  function toggleMenu() {
    menu.classList.toggle("active");
  }

  function loadImage(src) {
    return new Promise(function (resolve, reject) {
      var img = new Image();
      img.src = src;
      img.alt = "Galeria zdjęcie";
      img.onload = function () {
        resolve(img);
      };
      img.onerror = function () {
        reject(new Error("Nie udało się załadować obrazu: " + src));
      };
    });
  }

  function displayImages() {
    var promises = images.map(function (src) {
      return loadImage(src);
    });

    Promise.all(promises)
      .then(function (loadedImages) {
        loadedImages.forEach(function (img) {
          gallery.appendChild(img);
        });
      })
      .catch(function (error) {
        console.error(error);
        gallery.textContent = "Nie udało się załadować zdjęć.";
      });
  }

  toggle.addEventListener("change", toggleMenu);
  displayImages();
});
