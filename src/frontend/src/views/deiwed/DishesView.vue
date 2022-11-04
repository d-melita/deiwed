<!-- create dishes template to view all dishes available in a fast access table with name and price -->

<template>
  <v-card :flat="$store.getters.isMobile" class="table pb-4">
    <v-card-title>Pratos disponíveis para o DEI às Quartas</v-card-title>
    <v-card-text>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Pesquisar"
        single-line
        hide-details
      ></v-text-field>
      <v-data-table
        :headers="headers"
        :items="dishes"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Não existem pratos registados"
        no-results-text="Nenhum prato corresponde aos critérios indicados"
        sort-by="name"
      >
      <!-- type ? vegetarian: true, normal: false -->
        <template v-slot:[`item.vegetarian`]="{ item }">
          <v-chip
            v-if="item.vegetarian === true"
            color="green"
            text-color="white"
          >
            Vegetariano
          </v-chip>
          <v-chip v-else color="red" text-color="white"> Normal </v-chip>
        </template>
      </v-data-table>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component
export default class DishesView extends Vue {
  dishes: []  = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Nome', value: 'name', sortable: true, filterable: true },
    { text: 'Preço', value: 'unitPrice', sortable: true, filterable: true },
    { text: 'Descrição', value: 'description', sortable: true, filterable: true },
    { text: 'Peso', value: 'unitWeight', sortable: true, filterable: false },
    { text: 'Tipo', value: 'vegetarian', sortable: true, filterable: false }, // vegetarian or normal
];
  search = '';
  loading = true;

  async mounted() {
    await this.$store.dispatch('loading');
    try {
      this.dishes = await RemoteServices.getDishes();
      this.loading = false;
    } catch (error) {
      this.$store.dispatch('error', error);
    }
    await this.$store.dispatch('clearLoading');
  }
}
</script>