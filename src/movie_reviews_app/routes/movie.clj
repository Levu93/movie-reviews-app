(ns movie-reviews-app.routes.movie
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [ring.util.response :refer [response redirect content-type]]
            [movie-reviews-app.models.db :as db]))

(defn authenticated [session]
  (authenticated? session))

(defn israted [id user]
  (println (some? (first (db/find-rating-by-user-movie id user))))
  (some? (first (db/find-rating-by-user-movie id user))))

(defn getrating [id user]
  (db/find-rating-by-user-movie id user))

(defn averagerating [movie]
  (format "%.1f" (:average (first (db/get-ratings movie)))))

(defn check-authenticated-admin [session]
  (and (authenticated? session)
       (="admin" (:role (:identity session)))))

(defn showmovie [{:keys [params session] request :request}]
  (println (:identity session))
  (render-file "pages/moviedetail.html"
               {:movie (first (db/findbyid-movie (:id params)))
                :user (:identity session)
                :average (averagerating (:id params))
                :rating (first (db/find-rating-by-user-movie (:id params) (:username (:identity session))))
                :rated (str (israted (:id params) (:username (:identity session))))
                :comments (db/find-comment-by-movieid (:id params))
                :authenticated (str (authenticated session))}))

(defn addmovie-get [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
      (render-file "pages/addmovie.html" {:user (:identity session) :authenticated (str (authenticated session))})
      (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn addmovie-post [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
    (do (db/add-movie params) (redirect "/"))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn addcomment [{:keys [params session] request :request}]
  (if (authenticated session)
  (do (db/add-comment params) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn editcomment [{:keys [params session] request :request}]
  (if (authenticated session)
    (do (db/update-comment params) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn deletecomment [{:keys [params session] request :request}]
  (if (authenticated session)
   (do (db/delete-comment (:id params)) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn deletemovie [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
  (do (db/delete-movie (:id params))
    (redirect "/"))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn editmovie-get [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
      (render-file "pages/editmovie.html" {:user (:identity session) :authenticated (str (authenticated session)) :movie (first (db/findbyid-movie (:id params)))})
      (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn editmovie-post [{:keys [params session] request :request}]
  (if (check-authenticated-admin session)
    (do (db/update-movie params)
     (redirect "/"))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn ratemovie [{:keys [params session] request :request}]
  (if (authenticated session)
  (do (db/add-rating params) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn updaterating [{:keys [params session] request :request}]
  (if (authenticated session)
  (do (db/update-rating params) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defn deleterating [{:keys [params session] request :request}]
  (if (authenticated session)
   (do (db/delete-rating params) (redirect (str "/moviedetail/" (:movie params))))
    (render-file "pages/login.html" {:error "Please log in!!!"})))

(defroutes movie-routes
  (GET "/moviedetail/:id" request (showmovie request))
  (GET "/addmovie" request (addmovie-get request))
  (POST "/ratemovie" request (ratemovie request))
  (POST "/updaterating" request (updaterating request))
  (GET "/editmovie/:id" request (editmovie-get request))
  (POST "/addmovie" request (addmovie-post request))
  (POST "/editmovie/:id" request (editmovie-post request))
  (GET "/deletemovie/:id" request (deletemovie request))
  (POST "/addcomment" request (addcomment request))
  (POST "/editcomment" request (editcomment request))
  (GET "/deletecomment/:movie&:id" request (deletecomment request))
  (POST "/deleterating" request (deleterating request)))
