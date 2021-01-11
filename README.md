# Kirjahylly

Goodreadsin Developer key täytyy käydä syöttämässä xml-tiedostoon Kirjahylly\app\src\main\res\values\goodreads_devkey

Projekti käyttää Firebasea, joten luo oma Firebase projekti ja sinne Firestore tietokanta, jonka rakenne on seuraava:
```
{
  "users" : {
    "user1_id" : {
      "user1_shelf1" : {
        "book1" : {
          "title" : "book1"
          "img_url" : "link_to_img"
        }
      }
    },
    "user2_id" : {
      ...
    }
  }
}
```
Ota projektissa käyttöön myös Email/Password autentikointi kirjautumista varten.
