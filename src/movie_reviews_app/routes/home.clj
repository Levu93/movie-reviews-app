(ns movie-reviews-app.routes.home
  (:require [compojure.core :refer :all]
            [movie-reviews-app.views.layout :as layout]))

(defn home []
  (layout/common [:h1 "Hello World!"]))

(defroutes home-routes
  (GET "/" [] (home)))
