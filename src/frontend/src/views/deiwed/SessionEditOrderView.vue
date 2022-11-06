<template>
    <v-card :flat="$store.getters.isMobile" class="table pb-4">
        <v-card-title>Fazer encomenda para Sessão {{ session.id }}</v-card-title>
        <!-- selecionar um prato normal e um prato vegetariano -->
        <v-card-text>
            <v-row>
                <v-col cols="12" md="6">
                    <v-select
                        :items="normalDishes"
                        item-text="description"
                        item-value="id"
                        label="Prato Normal"
                        v-model="normalOptionDishId"
                    ></v-select>
                </v-col>
                <v-col cols="12" md="6">
                    <v-select
                        :items="vegDishes"
                        item-text="description"
                        item-value="id"
                        label="Prato Vegetariano"
                        v-model="vegetarianOptionDishId"
                    ></v-select>
                </v-col>
            </v-row>
        </v-card-text>
        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="EditOrder()">Editar encomenda</v-btn>
        </v-card-actions>
    </v-card>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';

@Component
export default class SessionMakeOrderView extends Vue {
    normalOptionDishId: number = 0;
    vegetarianOptionDishId: number = 0;
    
    session: SessionDto = new SessionDto();
    dishes: any[]  = [];
    normalDishes: any[] = [];
    vegDishes: any[] = [];

    async mounted() {
        this.session = await RemoteServices.getSession(parseInt(this.$route.params.id));
        this.dishes = await RemoteServices.getDishes();
        this.normalDishes = this.dishes.filter(dish => dish.vegetarian === false);
        this.vegDishes = this.dishes.filter(dish => dish.vegetarian === true);
    }

    EditOrder() {
        RemoteServices.editOrder(this.normalOptionDishId, this.vegetarianOptionDishId, this.session.date);
        this.$router.push('/sessions/' + this.session.id);
    }
}
</script>