create extension if not exists cube;
create extension if not exists earthdistance;

DELETE
FROM PROFILE
WHERE 1=1;

DELETE
FROM PREFERENCE
WHERE 1=1;

DELETE
FROM CANDIDATE
WHERE 1=1;

INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('b18a4f87-e5d8-4655-bee8-ed7b9d092293', 'Okan', '2000-11-19T14:08:23.445898400+08:00', 2, 800, 10, 10);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('d6c225ff-9cf0-4b90-b09e-3baf58e7c51c', 'b18a4f87-e5d8-4655-bee8-ed7b9d092293', 1, 18, 60, 50);

INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('8a2b0f1a-07ca-492a-8a9e-79e98c2d0e1b', 'Semih', '1919-06-19T14:08:23.445898400+08:00', 2, 800, 10, 10);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('76afa8de-f5a8-431e-9805-c0741e9bfbf0', '8a2b0f1a-07ca-492a-8a9e-79e98c2d0e1b', 1, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('5cf84f9b-8322-4fba-abf6-3ad5923ba643', 'Keder', '2011-07-19T14:08:23.445898400+08:00', 2, 800, 20, 20);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('71412b14-aa14-42bd-879a-68e2c77eb34d', '5cf84f9b-8322-4fba-abf6-3ad5923ba643', 1, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('99b9c951-333e-42c4-ba00-4eff9654d101', 'Casnoff', '2009-08-19T14:08:23.445898400+08:00', 2, 800, 10, 10);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('b801c2ae-6a20-473c-9a51-f5a7e3c4edcf', '99b9c951-333e-42c4-ba00-4eff9654d101', 1, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('1e189e25-8d71-4098-b841-0146b87d7d3b', 'Janna', '1987-09-19T14:08:23.445898400+08:00', 1, 800, 10, 10);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('9fed0cb5-9ea3-42c4-ad70-d34210f6e924', '1e189e25-8d71-4098-b841-0146b87d7d3b', 2, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('4351cc49-4e2e-4b33-bebf-3ce0b5706062', 'Tasha', '2004-01-19T14:08:23.445898400+08:00', 1, 800, 10, 10);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('675cc220-db73-4b9d-b48a-855085bd79db', '4351cc49-4e2e-4b33-bebf-3ce0b5706062', 2, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('7e5122f7-2039-4b5f-9191-390efad0585f', 'Natalie', '2019-02-19T14:08:23.445898400+08:00', 1, 800, 20, 20);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('20625f7f-c783-4340-b5b7-7f7750f78a27', '7e5122f7-2039-4b5f-9191-390efad0585f', 2, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('bd3f24b0-ec4c-486a-84b8-e75249201d1f', 'Anna', '2012-03-19T14:08:23.445898400+08:00', 1, 800, 25, 25);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('83335818-2e94-40c5-9f95-fd5535bbd45b', 'bd3f24b0-ec4c-486a-84b8-e75249201d1f', 2, 18, 60, 50);


INSERT INTO PROFILE (id,
                     name,
                     birthday,
                     gender,
                     elo_ranking,
                     lat,
                     lon)
VALUES ('7fddfa3e-4ff4-4c26-927c-cc4c6c8871b2', 'Denis', '2011-03-19T14:08:23.445898400+08:00', 1, 800, 30, 30);
INSERT INTO PREFERENCE (id, profile_id, gender, min_age, max_age, search_distance)
VALUES ('fef5ec8e-d953-4f75-b62a-58bebfb42496', '7fddfa3e-4ff4-4c26-927c-cc4c6c8871b2', 2, 18, 60, 50);
