(ns my-webapp.views
  (:require [my-webapp.db :as db]
            [clojure.string :as str]
            [hiccup.page :as page]
            [ring.util.anti-forgery :as util]))

(defn page-head
  [title]
  [:head
   [:title (str "Locations: " title)]
   (page/include-css "/css/styles.css")]
)

(def header-links
  [:div#header-links
   "[ "
   [:a {:href "/"} "Home"] ;; im guessing this these are links and routes
   " | "
   [:a {:href "/add-location"} "Add a Location"]
   " | "
   [:a {:href "/all-locations"} "View all Locations"]
   " ]"])

(defn home-page 
  []
  (page/html5
    (page-head "Home")
    header-links
    [:h2 "Hi Paul"])
  )

(defn add-task-page
  []
  (page/html5
   (page-head "Add a Location")
   header-links
   [:h1 "Add a Location"]
   [:form {:action "/add-location"  :method "POST"}
    (util/anti-forgery-field) ; prevents cross-site scripting attacks (from docs)
    [:p "x " [:input {:type "text" :name "x"}]]
    [:p "y " [:input {:type "text" :name "y"}]]
    [:p [:input {:type "submit" :value "submit locations"}]]
]) 
) 
(defn add-task-results-page

  [{:keys [x y]}]
  (let [id (db/add-location-to-db x y)];; i want to log this
   (page/html5
    (page-head  "Added a Location")
    header-links
    [:h1 "Added a location"]
    [:p "Added [" x ", " y "] (id: " id ") to the db. "
      [:a {:href (str "/location/" id)} "See for yourself"] "."])) ) 

 

(defn task-page 
  [id]
  (let [{x :x y :y} (db/get-xy id)]
    (page/html5
     (page-head (str "Locations" id))
      header-links
      [:h1 "A Single Location"]
      [:p "id: " id]
      [:p "x: " x]
      [:p "y: " y])))

(defn all-tasks-page
  []
  (let [all-locs (db/get-all-locations)]
    (page/html5
      (page-head "All Locations in the db")
      header-links
      [:h1 "All Locations"]
      [:table
      [:tr [:th "id"] [:th "x"] [:th "y"]]
      (for [loc all-locs]
        [:tr [:td (:id loc)] [:td (:x loc)] [:td (:y loc)]])])))
        



