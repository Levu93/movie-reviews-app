(ns movie-reviews-app.routes.home
  (:require [compojure.core :refer :all]
            [selmer.parser :refer [render-file]]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [movie-reviews-app.models.db :as db]))

(defn authenticated [session]
  (authenticated? session))

(defn home [{:keys [params session] request :request}]
  (render-file "pages/home.html" {:movies (db/get-movies) :user (:identity session) :authenticated (str (authenticated session))}))

(defroutes home-routes
  (GET "/" request (home request)))
