<!-- add attendee page - add attendee with name, istId, type -->
<template>
    <v-card>
        <v-card-title>
        <h1 class="display-1">Adicionar Sessão</h1>
        </v-card-title>
        <v-card-text>
        <v-form>
            <!-- name not empty -->
            <v-text-field
            v-model="date"
            label="Data (aaaa/mm/dd)"
            required
            :rules="[value => !!value || 'Data é obrigatório']"
            ></v-text-field>
            <v-text-field
            v-model="theme"
            label="Tema"
            required
            :rules="[value => !!value || 'Tema é obrigatório']"
            ></v-text-field>
            <v-text-field
            v-model="speaker"
            label="Orador"
            required
            :rules="[value => !!value || 'Orador é obrigatório']"
            ></v-text-field>
        </v-form>
        <!-- button bottom right -->
        <v-btn color="primary" class="" @click="addSession">
            <v-icon left>mdi-plus</v-icon> Adicionar Sessão
        </v-btn>
        <!-- cancel button bottom left -->
        <v-btn color="error" class="" @click="goTo('/sessions')">
            <v-icon left>mdi-close</v-icon> Cancelar
        </v-btn>
        </v-card-text>
    </v-card>
</template>

<script lang="ts">
import AttendeeDto from '@/models/deiwed/AttendeeDto';
import SessionDto from '@/models/deiwed/SessionDto';
import RemoteServices from '@/services/RemoteServices';
import { Component, Vue } from 'vue-property-decorator';

@Component
export default class AddSessionView extends Vue {
  date: string = '';
  theme: string = '';
  speaker: string = '';
  image: string = '';

  async addSession() {
    const session: SessionDto = {
        id: 0,
        date: this.date,
        theme: this.theme,
        speaker: this.speaker,
        image: this.image,
    };
    await RemoteServices.addSession(session);
    this.$router.push('/sessions');
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