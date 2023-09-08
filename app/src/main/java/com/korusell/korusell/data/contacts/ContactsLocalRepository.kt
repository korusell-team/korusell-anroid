package com.korusell.korusell.data.contacts

import com.korusell.korusell.data.contacts.model.Category
import com.korusell.korusell.data.contacts.model.Contact

//TODO("This is Simple Data Repository")
class ContactsLocalRepository : ContactsRepositoryImpl {

    override fun getFilters(): List<Category> {
        return listOf(
            Category(
                name = "Дизайн",
                subCategories = listOf(
                    "UI/UX",
                    "Web",
                    "Графический дизайн"
                )
            ),
            Category(
                name = "IT",
                subCategories = listOf(
                    "Программирование",
                    "Приложения",
                    "iOS",
                    "Android",
                    "Сайты"
                )
            ),
            Category(
                name = "Образование",
                subCategories = listOf(
                    "Смена-Визы",
                    "Корейский язык",
                    "Дизайн",
                    "Программирование",
                    "Школа",
                    "Автошкола"
                )
            ),
            Category(
                name = "Мероприятия",
                subCategories = listOf(
                    "Ведущий",
                    "Тамада",
                    "Вокалист",
                    "Певец",
                    "Цветы"
                )
            ),
            Category(
                name = "Фото-Видео",
                subCategories = listOf(
                    "Видеограф",
                    "Фотограф",
                    "Праздник",
                    "Мероприятия"
                )
            ),
            Category(
                name = "Маркетинг",
                subCategories = listOf(
                    "Продажи",
                    "Продвижение",
                    "SMM"
                )
            ),
            Category(
                name = "Переводы",
                subCategories = listOf(
                    "Переводчик",
                    "Корейский",
                    "Английский"
                )
            ),
            Category(
                name = "Здоровье-Красота",
                subCategories = listOf(
                    "Стоматология",
                    "Косметика",
                    "Тату",
                    "Парикмахер",
                    "Визажист",
                    "Маникюр",
                    "Педикюр",
                    "Ресницы",
                    "Пластическая хирургия",
                    "Гинекология"
                )
            ),
            Category(
                name = "Транспорт",
                subCategories = listOf(
                    "Перевозки",
                    "Купля-Продажа",
                    "Экспорт",
                    "СТО",
                    "Тюнинг",
                    "Электрик"
                )
            ),
            Category(
                name = "Ремонт",
                subCategories = listOf(
                    "Электроника",
                    "Квартиры",
                    "Сантехник",
                    "Электрик"
                )
            ),
        )

    }

    override fun getContacts(): List<Contact> {
        return listOf(
            Contact(
                name = "Евгений",
                surname = "Ким",
                bio = "Лучший Ведущий мероприятий\nв Южной Корее!",
                image = listOf("evgeniy-hvan"),
                categories = listOf("Мероприятия"),
                subcategories = listOf("Ведущий", "Тамада"),
                phone = "01012341234",
                instagram = "https://instagram.com/vlog.vedushego?igshid=OGQ5ZDc2ODk2ZA=="
            ),
            Contact(
                name = "Сергей",
                surname = "Ли",
                bio = "\niOS",
                image = listOf("sergey-lee"),
                categories = listOf("IT"),
                subcategories = listOf("Программирование", "Приложения", "iOS")
            ),
            Contact(
                name = "Антон",
                surname = "Емельянов",
                bio = "fullstack\ndeveloper",
                categories = listOf("IT"),
                subcategories = listOf("Программирование", "Сайты")
            ),
            Contact(
                name = "Андрей",
                surname = "Ким",
                bio = "я просто рандомный чел...🤪",
                categories = listOf("Переводы"),
                subcategories = listOf("Английский")
            ),
            Contact(
                name = "Владимир",
                surname = "Мун",
                bio = "Habsida. Школа программирования и дизайна. С оплатой после трудоустройства!",
                cities = listOf("Сеул"),
                image = listOf("vladimir-mun", "vladimir-mun2"),
                categories = listOf("Образование"),
                subcategories = listOf("Дизайн", "Программирование"),
                phone = "010-1234-1234",
                instagram = "munvova",
                link = "https://habsida.com/ru",
                telegram = "vladimun"
            ),
            Contact(
                name = "Владимир",
                surname = "Тен",
                bio = "Основатель школы корейского языка 'Korean Simple' и далее длинное описание услуг которые может предоставить данный человек",
                image = listOf("vladimir-ten", "vladimir-ten2"),
                categories = listOf("Образование"),
                subcategories = listOf("Корейский язык"),
                phone = "01012341234",
                instagram = "https://instagram.com/vladimirten?igshid=OGQ5ZDc2ODk2ZA=="
            ),
            Contact(
                name = "David",
                surname = "Beckham",
                bio = "I'm here to check if english content displays properly",
                image = listOf("david-beckham"),
                categories = listOf("Маркетинг"),
                subcategories = listOf("Продажи"),
                instagram = "https://www.instagram.com/davidbeckham"
            ),
        )
    }
}