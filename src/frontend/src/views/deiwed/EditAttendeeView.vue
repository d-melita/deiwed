<!-- edit attendee page - edit attendee with name, istId, type -->
<template>
    <v-card>
        <v-card-title>
        <h1 class="display-1">Editar Participante</h1>
        </v-card-title>
        <v-card-text>
        <v-form>
            <!-- name not empty -->
            <v-text-field
            v-model="name"
            label="Nome"
            required
            :rules="[value => !!value || 'Nome é obrigatório']"
            ></v-text-field>
            <v-text-field
            v-model="istId"
            label="IST ID"
            required
            :rules="[value => !!value || 'IST ID é obrigatório']"
            ></v-text-field>
            <v-select
            v-model="type"
            :items="['Professor', 'Bolseiro']"
            label="Tipo"
            required
            :rules="[value => !!value || 'Tipo é obrigatório']"
            ></v-select>
        </v-form>
        <!-- button bottom right -->
        <v-btn color="primary" class="" @click="editAttendee">
            <v-icon left>mdi-plus</v-icon> Editar Participante
        </v-btn>
        <!-- cancel button bottom left -->
        <v-btn color="error" class="" @click="goTo('/attendees')">
            <v-icon left>mdi-close</v-icon> Cancelar
        </v-btn>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class editAttendee extends Vue {
  id: number = 0;
  name: string = '';
  istId: string = '';
  type: string = '';

  async editAttendee() {
    const attendee: AttendeeDto = {
        id: parseInt(this.$router.currentRoute.params.id),
        name: this.name,
        istId: this.istId,
        type: this.type,
    };
    await RemoteServices.editAttendee(attendee);
    this.$router.push('/attendees');
  }

  goTo(path: string): void {
    if (this.$route.path == path) {
      this.$router.go(0);
    } else {
      this.$router.push(path);
    }
  }
}
</script>