(ns movie-reviews-app.routes.home
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [movie-reviews-app.models.db :as db]))

(defn home [{:keys [params session] request :request}]
  (render-file "pages/home.html" {:movies (db/get-movies) :user (:identity session)}))

(defroutes home-routes
  (GET "/" request (home request)))
