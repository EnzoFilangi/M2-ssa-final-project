## <p align="center">Great Intern Follow-up Tool</p>
### <p align="center">M2SE - Advanced programming - Team 3</p>
##### <p align="center">Samuel BADER, Enzo FILANGI</p>

---

## The project

The repository can be found here: [https://github.com/EnzoFilangi/M2-ssa-final-project](https://github.com/EnzoFilangi/M2-ssa-final-project)

The goal of this project is to build an app to track the internships of a group of students using JEE.

We used React for the frontend, and Jax-RS for the backend.

The deployed solution is available at: [https://ap.enzofilangi.fr/](https://ap.enzofilangi.fr/)

## Installation

To install the project locally, simple clone this repo and run the following command at the project's root:
```Bash
docker compose -f docker-compose.dev.yml up -d --build
```

Then navigate to [http://localhost:30000/](http://localhost:30000/)

PostgreSQL will automatically restore a dev database from `base_dev_db.sql` at startup. This operation might take a few seconds, so give it a bit of time.

## Usage

Here is a video that details the use of our website: https://www.youtube.com/watch?v=-0iU9iPCX6s

You can also explore it by yourself. Here are some valid credentials:
```
username: ja@efrei.fr
password: @#4EJZwd
---
username: ef@efrei.net
password: FYk#g7M~
```

Finally, you can also explore the backend directly by making HTTP requests to localhost:30001. To aid with this, we joined an export of an insomnia project: `Advanced_Programming_Insomnia.json`. You only have to import the project into Insomnia and update the UUIDs in the different URLs to test the backend this way.
