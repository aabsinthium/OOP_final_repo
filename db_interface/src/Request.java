public class Request {
    private final String data_path_;
    private final String save_path_;

    public Request(String data_path, String save_path){
        this.data_path_ = data_path; // при создании указывается путь к базе данных, возможно из настроек
        this.save_path_ = save_path; // путь к директории для сохранения финального файла
        // прописать дефолтный
    }

    public void formRequest(boolean[] status){
        // вызов при отправке запроса
        // создание запроса:
        // подгрузка файлов, парсинг большой таблицы
        // парсинг требуемых колонок
    }

    public void getFile(){
        // добавить кнопку создания файла
        // запись данных в файл в указанной директории
    }
}
