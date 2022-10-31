package pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.dto.DishDto;

@Entity
@Table(name = "dishes")
public class Dish {
    
        @Id
        @GeneratedValue
        private Long id;
    
        @Column(name = "name", nullable = false)
        private String name;
    
        @Column(name = "price", nullable = false)
        private double price;
    
        @Column(name = "type", nullable = false)
        private String type;

    
        protected Dish() {
        }
    
        public Dish(String name, double price, String type) {
            this.name = name;
            this.price = price;
            this.type = type;
        }
    
        public Dish(DishDto dishDto) {
            this(dishDto.getName(), dishDto.getPrice(), dishDto.getType());
        }

        public Long getId() {
            return this.id;
        }
    
        public String getName() {
            return this.name;
        }
    
        public double getPrice() {
            return this.price;
        }
    
        public String getType() {
            return this.type;
        }

        @Override
        public String toString() {
            return String.format(
                "Dish[id=%d, name='%s', price='%f', type='%s']", 
                id, name, price, type);
        }
}