(ns my-webapp.handler
  (:require [my-webapp.views :as views]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(defroutes app-routes
  (GET "/" 
       [] 
       (views/home-page))
  (GET "/add-location"
       []
       (views/add-task-page))
  (POST "/add-location"
        {params :params}
        (views/add-task-results-page params))
  (GET "/location/:id"
       [id]
       (views/task-page id))
  (GET "/all-locations"
       []
       (views/all-tasks-page))
  
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

(defn -main 
  [& [port]]
  (let [port (Integer. (or port 
                           (System/getenv "PORT")
                           5000))]
    (jetty/run-jetty #'app {:port port :join? false })))

