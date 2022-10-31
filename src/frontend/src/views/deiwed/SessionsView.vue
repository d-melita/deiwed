<!-- create sessions template to view all sessions available in a fast access table with date, theme and speaker -->
<template>
  <v-card>
    <v-card-title>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
    </v-card-title>
    <v-card-text>
      <v-data-table
        :headers="headers"
        :items="sessions"
        :search="search"
        :loading="loading"
        single-select
        locale="pt-PT"
        no-data-text="Não existem sessões registadas"
        no-results-text="Nenhuma sessão corresponde aos critérios indicados"
        sort-by="date"
      >
        <template v-slot:[`item.theme`]="{ item }">
        </template>
      </v-data-table>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';

@Component
export default class SessionsView extends Vue {
  sessions: SessionDto[] = [];
  headers: DataTableHeader[] = [
    { text: 'ID', value: 'id', sortable: true, filterable: true },
    { text: 'Data', value: 'date', sortable: true, filterable: true },
    { text: 'Tema', value: 'theme', sortable: true, filterable: true },
    { text: 'Orador', value: 'speaker', sortable: true, filterable: true },
];
  search = '';
  loading = true;
}
</script>