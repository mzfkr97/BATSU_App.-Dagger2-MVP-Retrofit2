package com.example.batsu.utils.database;

public class Retriever {


    /*NТут собраны все основные запросы к базе данных по вопросу автобусов. Все запросы возвращают список.
    * вызывается примерно такю Сюда передаем базу данных для работы
    * if(getActivity()!= null){
    * database = ((MyApplication)getActivity().getApplication()).getDatabase();
    * retriever = new Retriever(database);

        *
        *
    * */

//    private static final String TABLE_BUS = "bus_list";
//    private MyDatabase database;
//    private List<Bus_List> busLists = new ArrayList<>();
//    private List<Bus_Station_List> busStationList = new ArrayList<>();
//    private List<ItemListStation> stationList = new ArrayList<>();
//    private List<PojoDatabases> medicList = new ArrayList<>();
//
//
//
//    public Retriever(MyDatabase database) {
//        this.database = database;
//    }
//
//
//    //EndStationMainFrag
//    public List<Bus_List> retrieveEndStationMainFrag() {
//        Cursor cursor = database.getAllData(TABLE_BUS);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String bus_number = cursor.getString(1);
//            String start_station = cursor.getString(2);
//            String end_station = cursor.getString(3);
//            Bus_List busList = new Bus_List(bus_number, start_station, end_station);
//            busLists.add(busList);
//        }
//        database.closeDB();
//        return busLists;
//    }
//
//
//    //ListStationFrag
//    public List<ItemListStation> retrieveListStationFrag(String table_name) {
//        stationList.clear();
//        Cursor cursor = database.getAllData(table_name);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String name = cursor.getString(1);
//            String to_station = cursor.getString(2);
//            String station_id = cursor.getString(3);
//            ItemListStation p = new ItemListStation(_id, name, to_station, station_id);
//            stationList.add(p);
//        }
//        database.closeDB();
//        return stationList;
//    }
//
//
//
//
//    /*@link MainStationFrag
//     * */
//    public List<Bus_List> retrieveBusEndPoint(String bus_num, String end_station) {
//        Cursor cursor = database.getListStation(bus_num);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String bus_number = cursor.getString(1);
//            String start_station = cursor.getString(2);
//            String working_days = cursor.getString(3);
//            String work_time = cursor.getString(4);
//            String weekend = cursor.getString(5);
//            String weekend_time = cursor.getString(6);
//            String all_station = cursor.getString(7);
//            String key_id = cursor.getString(8);
//            Bus_List p = new Bus_List(bus_number, start_station, end_station, working_days, work_time, weekend, weekend_time, all_station, key_id);
//            busLists.add(p);
//        }
//        database.closeDB();
//        return busLists;
//    }
//
//
//    /**
//     * link:{Fragment_Bus_Details}
//     */
//    public List<Bus_Station_List> retrieveEndPointToStation(String key_id_string) {
//        Cursor cursor = database.getBusStation(key_id_string);
//        busStationList.clear();
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String bus_number = cursor.getString(1);
//            String start_station = cursor.getString(2);
//            String work_day = cursor.getString(3);
//            String work_day_time = cursor.getString(4);
//            String holiday_day = cursor.getString(5);
//            String holiday_day_time = cursor.getString(6);
//            String destination = cursor.getString(7);
//            long position_sort = cursor.getLong(8);
//            String key_id = cursor.getString(9);
//            String station_id = cursor.getString(10);
//
//            Bus_Station_List p = new Bus_Station_List(bus_number, start_station, work_day, work_day_time,
//                    holiday_day, holiday_day_time, destination, station_id);
//            busStationList.add(p);
//
//        }
//        database.closeDB();
//        return busStationList;
//    }
//
//    /**
//     * link:{FindStationFragment}
//     */
//    public List<ItemListStation> retrieveFindStation(String station_toolbar) {
//        stationList.clear();
//        Cursor cursor = database.findStation(station_toolbar);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String name = cursor.getString(1);
//            String to_station = cursor.getString(2);
//            String station_id = cursor.getString(3);
//            ItemListStation p = new ItemListStation(_id, name, to_station, station_id);
//            stationList.add(p);
//
//        }
//        database.closeDB();
//        return stationList;
//    }
//
//
//    /**
//     * link:{Fragment_Station_Detail}
//     */
//    public List<Bus_Station_List> retrieveFragmentStationDetail(String station_id_int) {
//        busStationList.clear();
//        Cursor cursor = database.getListFragmentStation(station_id_int);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String bus_number = cursor.getString(1);
//            String start_station = cursor.getString(2);
//            String work_day = cursor.getString(3);
//            String work_day_time = cursor.getString(4);
//            String holiday_day = cursor.getString(5);
//            String holiday_day_time = cursor.getString(6);
//            String destination = cursor.getString(7);
//            long position_sort = cursor.getLong(8);
//            String key_id = cursor.getString(9);
//            String station_id = cursor.getString(10);
//            Bus_Station_List p = new Bus_Station_List(bus_number, start_station, work_day, work_day_time,
//                    holiday_day, holiday_day_time, destination, station_id);
//            busStationList.add(p);
//        }
//        database.closeDB();
//        return busStationList;
//    }
//
//    public List<PojoDatabases> retrieveMedicsDetail(String keyQuery) {
//        busStationList.clear();
//        Cursor cursor = database.findMedics(keyQuery);
//        while (cursor.moveToNext()) {
//            long _id = cursor.getLong(0);
//            String floor = cursor.getString(1);
//            String description = cursor.getString(2);
//            String phone = cursor.getString(3);
//            String key_query = cursor.getString(4);
//
//            PojoDatabases p = new PojoDatabases(_id, floor, description, phone, key_query);
//            medicList.add(p);
//        }
//        database.closeDB();
//        return medicList;
//    }



}
